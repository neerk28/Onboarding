package com.intuitcraft.onboarding.storage;


import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

public class S3Util {
    private static final String BUCKET = "craft-onboarding";

    public static String uploadFile(String fileName)
            throws AwsServiceException, SdkClientException {
        S3Client client = S3Client.builder().build();

        S3Waiter waiter = client.waiter();
        HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();

        WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);

        boolean uploaded = waitResponse.matched().response().isPresent();
        System.out.println("Url: " + "https://" + BUCKET + ".s3.ap-south-1.amazonaws.com/" + fileName);
        if(uploaded)
            return "https://" + BUCKET + ".s3.ap-south-1.amazonaws.com/" + fileName;
        return "";
    }
}
