package com.safecode.javacv.service;

import org.springframework.util.concurrent.ListenableFuture;

public interface FaceLoginService {


    ListenableFuture<Boolean> faceissuccess(String imgpath, String filepath);

    ListenableFuture<String> register(String base64code, String filepath, String useraccount, int userid);

}