package com.its.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.its.member.dto.MemberDTO;
import com.its.member.entity.Member;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository mr;


    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberFile = memberDTO.getMemberProfile();
        if (!memberFile.isEmpty()) {
            String memberFileName = memberFile.getOriginalFilename();
            memberFileName = System.currentTimeMillis() + "_" + memberFileName;
            String savePath = "D:\\springboot_img\\" + memberFileName;
            memberFile.transferTo(new File(savePath));
            memberDTO.setMemberProfileName(memberFileName);
        }
        Long saveId = mr.save(Member.toSaveEntity(memberDTO)).getId();
        return saveId;
    }

    public MemberDTO findById(Long id) {
        Optional<Member> member = mr.findById(id);
        if (member.isPresent()) {
            return MemberDTO.toMemberDTO(member.get());
        } else {
            return null;
        }
    }

    public MemberDTO loginCheck(MemberDTO memberDTO) {
        MemberDTO findDTO = findByMemberEmail(memberDTO.getMemberEmail());
        if (memberDTO.getMemberPassword().equals(findDTO.getMemberPassword())) {
            return findDTO;
        } else {
            return null;
        }
    }


    public void update(MemberDTO memberDTO) throws IOException {
        MultipartFile updateFile = memberDTO.getMemberProfile();
        if (!updateFile.isEmpty()) {
            String updateFileName = updateFile.getOriginalFilename();
            updateFileName = System.currentTimeMillis() + "_" + updateFileName;
            String updatePath = "D:\\springboot_img\\" + updateFileName;
            updateFile.transferTo(new File(updatePath));
            memberDTO.setMemberProfileName(updateFileName);
        }
        mr.save(Member.toUpdateEntity(memberDTO));
    }

    public void delete(Long id) {
        mr.deleteById(id);
    }

    public List<MemberDTO> findAll() {
        List<Member> memberList = mr.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member m: memberList) {
            memberDTOList.add(MemberDTO.toMemberDTO(m));
        }
        return memberDTOList;
    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        Optional<Member> optionalMember = mr.findByMemberEmail(memberEmail);
        if (optionalMember.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMember.get());
        } else {
            return null;
        }
    }

    public String kakaoLogin(String code) {
        String access_token = "";
        String refresh_token = "";
        String requestURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // POST ????????? ?????? ???????????? false??? setDoOutput??? true???
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // post ????????? ????????? ???????????? ???????????? ???????????? ?????? ??????
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("grant_type=authorization_code");
            // TODO REST_API_KEY ??????
            stringBuilder.append("&client_id=0aaae818ba0f16fdeb1caea5a5238f33");
            // TODO ?????????????????? redirect_uri ??????
            stringBuilder.append("&redirect_uri=http://localhost:8080/member/kakao");
            stringBuilder.append("&code=" + code);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();

            // ??????????????? 200????????? ??????
            int responseCode = connection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            // ????????? ?????? ?????? JSON ????????? Response ????????? ????????????
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            System.out.println("result = " + result);

            // Gson ?????????????????? ????????? ???????????? JSON ?????? ?????? ??????
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(result);

            access_token = jsonElement.getAsJsonObject().get("access_token").getAsString();
            refresh_token = jsonElement.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("accessToken = " + access_token);
            System.out.println("refreshToken = " + refresh_token);

            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_token;
    }

//    public void createKakaoUser(String token) {
//
//        String reqURL = "https://kapi.kakao.com/v2/user/me";
//
//        //access_token??? ???????????? ????????? ?????? ??????
//        try {
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Authorization", "Bearer " + token); //????????? header ??????, access_token??????
//
//            //?????? ????????? 200????????? ??????
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);
//
//            //????????? ?????? ?????? JSON????????? Response ????????? ????????????
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//            String result = "";
//
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//            System.out.println("response body : " + result);
//
//            //Gson ?????????????????? JSON??????
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);
//
//            int id = element.getAsJsonObject().get("id").getAsInt();
//            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
//            String email = "";
//            if(hasEmail){
//                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
//            }
//
//            System.out.println("id : " + id);
//            System.out.println("email : " + email);
//
//            br.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

        public HashMap<String, Object> createKakaoUser(String access_Token) {
            //    ???????????? ????????????????????? ?????? ????????? ?????? ??? ????????? HashMap???????????? ??????
            HashMap<String, Object> userInfo = new HashMap<>();
            String reqURL = "https://kapi.kakao.com/v2/user/me";
            try {
                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                //    ????????? ????????? Header??? ????????? ??????
                conn.setRequestProperty("Authorization", "Bearer " + access_Token);

                int responseCode = conn.getResponseCode();
                System.out.println("responseCode : " + responseCode);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";
                String result = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }
                System.out.println("response body : " + result);

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
                JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

                String nickname = properties.getAsJsonObject().get("nickname").getAsString();
                String email = kakao_account.getAsJsonObject().get("email").getAsString();

                userInfo.put("nickname", nickname);
                userInfo.put("email", email);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return userInfo;
        }


    public void kakaoLogout(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
