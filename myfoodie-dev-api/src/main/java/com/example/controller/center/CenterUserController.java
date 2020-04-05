package com.example.controller.center;

import com.example.pojo.Users;
import com.example.pojo.bo.center.CenterUserBO;
import com.example.resource.FileUpload;
import com.example.service.center.CenterUserService;
import com.example.utils.CookieUtils;
import com.example.utils.DateUtil;
import com.example.utils.IMOOCJSONResult;
import com.example.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.HttpMethod;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息接口",tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;


    @PostMapping("update")
    @ApiOperation(value = "更新用户信息",httpMethod ="POST")
    public IMOOCJSONResult update(@ApiParam(name = "userId") String userId,
                                  @RequestBody @Valid CenterUserBO centerUserBO,
                                  BindingResult result,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        if (result.hasErrors()) {
            Map m = getErrors(result);
            return IMOOCJSONResult.errorMap(result);
        }

        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);

        setNullProperty(userResult);
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);

        // TODO 后续要改，增加令牌token，会整合进redis，分布式会话
        return IMOOCJSONResult.ok();
    }

    private Map getErrors(BindingResult result) {
        Map<String,String> m = new HashMap();
        List<FieldError> errorList = result.getFieldErrors();
        errorList.stream().forEach(
                e->m.put(e.getField(),e.getDefaultMessage())
        );
        return m;
    }


    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }


    @PostMapping("uploadFace")
    @ApiOperation(value = "上传用户图片",httpMethod = "POST")
    public IMOOCJSONResult uploadFace (
            @ApiParam(name = "userId",value = "用户Id",required = true)
            @RequestParam String userId,
            @ApiParam(name = "file",value = "头像文件",required = true)
            MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) {

        if(file==null) {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }

        Users user = centerUserService.queryUserInfo(userId);
        String[] fileInfos = file.getOriginalFilename().split("\\.");
        String uploadImgName = fileInfos[0];
        String suffix =fileInfos[1];
        FileOutputStream fileOutStream = null;
        String imgUrl = "";



        //文件上传校验，防止他人上传.sh,.php等文件进行攻击
        if(!suffix.equalsIgnoreCase("jpg")&&
                !suffix.equalsIgnoreCase("png")&&
                !suffix.equalsIgnoreCase("jpeg")) {
            return IMOOCJSONResult.errorMsg("文件格式不正确");
        }




        try {
            //文件上传根路径
            String faceRootPath = fileUpload.getImageUserFaceLocation();
            //每个用户的文件上传根路径
            String userFaceRootPath = faceRootPath + File.separator + userId;

            //图片名称
            // face-{userid}.png
            // 文件名称重组 覆盖式上传，增量式：额外拼接当前时间
            String facefileName = "face-"+userId+"."+suffix;
            //上传文件最终绝对路径
            String finalImgPath = userFaceRootPath+File.separator+facefileName;

            //文件访问url
            imgUrl = fileUpload.getImageServerUrl()+userId+"/"+facefileName;

            File imgfile = new File(finalImgPath);
            //上级文件路径不存在，則创建
            if (!imgfile.getParentFile().exists()) {
                imgfile.getParentFile().mkdirs();
            }

            fileOutStream = new FileOutputStream(imgfile);
            InputStream inputStream = file.getInputStream();
            IOUtils.copy(inputStream,fileOutStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileOutStream!=null) {
                try {
                    fileOutStream.flush();
                    fileOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //由于浏览器可能有缓存，这里加上时间戳
        imgUrl+="?t="+ DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        user.setFace(imgUrl);
        Users finalUser = centerUserService.updateUserFace(userId, imgUrl);

        setNullProperty(finalUser);
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(finalUser),true);

        // TODO 后续要改，增加令牌token，会整合进redis，分布式会话


        return IMOOCJSONResult.ok();

    }




}
