package com.intuitcraft.onboarding.projectManagement;

import com.intuitcraft.onboarding.model.FileMeta;
import com.intuitcraft.onboarding.model.FileType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.HashMap;
import java.util.Map;

public class TrelloClient {

    static Map<FileType, String> listMap = new HashMap<>();
    static{
        listMap.put(FileType.PAN,"63318574a9e886013ba826c2");
        listMap.put(FileType.PROFILE_PHOTO,"633172fe789e26054e452251");
        listMap.put(FileType.DL,"6331867e1523f103c8dcfda6");
        listMap.put(FileType.RC,"63318690715e7e0332f019dc");
    }
    public static String  createCard(String url, FileMeta meta){
        HttpResponse<JsonNode> response = Unirest.post("https://api.trello.com/1/cards")
                .header("Accept", "application/json")
                .queryString("idList",listMap.get(meta.getFileType()))
                .queryString("key", "81ba282012c2b24b32bec7fe0d39dd1e")
                .queryString("token", "40bbbf6e599a3156f6e65710196a5a563bdaef53417afc84644e52276b51e9ef")
                .queryString("name","User " + meta.getId())
                .queryString("urlSource",url)
                .queryString("desc", "Verify " + meta.getFileType())
                .asJson();
        return (String) response.getBody().getObject().get("id");
    }
}
