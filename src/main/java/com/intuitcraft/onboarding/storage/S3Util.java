package com.intuitcraft.onboarding.storage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class S3Util {
    private static final String BUCKET = "craft-driver-onboarding";

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Util.class);
    public static String uploadFile(String fileName, InputStream inputStream)
            throws AwsServiceException, SdkClientException, IOException {

        try {
            LOGGER.info("Uploading a file to S3 - {}", fileName);
            S3Client client = S3Client.builder().build();
            PutObjectResponse putObjectResult = client.putObject(
                    PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(fileName)
                            .build(), RequestBody.fromInputStream(inputStream, inputStream.available()));
            final URL reportUrl = client.utilities().getUrl(GetUrlRequest.builder().bucket(BUCKET).key(fileName).build());
            LOGGER.info("putObjectResult = " + putObjectResult);
            LOGGER.info("reportUrl = " + reportUrl);
            return reportUrl.toString();
        } catch (SdkServiceException ase) {
            LOGGER.error("Caught an AmazonServiceException, which " + "means your request made it "
                    + "to Amazon S3, but was rejected with an error response" + " for some reason.", ase);
            LOGGER.error("Error Message:    " + ase.getMessage());
            LOGGER.error("Key:       " + fileName);
            throw ase;
        } catch (SdkClientException ace) {
            LOGGER.error("Caught an AmazonClientException, which " + "means the client encountered "
                    + "an internal error while trying to " + "communicate with S3, "
                    + "such as not being able to access the network.", ace);
            LOGGER.error("Error Message: {}, {}", fileName, ace.getMessage());
            throw ace;
        }
    }
}
