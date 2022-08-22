package com.adnstyle.boardsj.util;

import com.adnstyle.boardsj.dto.AttachDto;
import com.adnstyle.boardsj.dto.ItemDto;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

//    개발자가 직접 작성한 클래스를 스프링 컨테이너에 등록하는데 사용
@Component
public class FileUtil {
    /*
    * 업로드 경로 설정
    * */
    private final String uploadPath = Paths.get("D:","board","file").toString();

    /*
    * 랜덤문자열 생성
    * */
    private final String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /*
    * 서버에 첨부파일을 생성하고, 업로드 파일 목록 반환한다.
    * */
    public List<AttachDto> uploadFiles(MultipartFile[] files, int refKey){
        if(files[0].isEmpty()){//  파일이 비어있으면 비어있는 리스트 반환
            return Collections.emptyList();
        }

        List<AttachDto> attachList = new ArrayList<>();//  업로드 파일 정보를 담을 비어있는 리스트
        File dir = new File(uploadPath);//  uploadPath에 해당하는 디렉토리가 존재하지 않으면, 부모 디렉토리를 포함하 모든 디렉토리를 생성
        if(dir.exists() == false){
            dir.mkdir();
        }

        for(MultipartFile file : files){//  파일 갯수만큼 forEach
            try{
                final String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 파일 확장자
                final String saveName = getRandomString() + "." + extension; // 서버에 저장할 파일명(랜덤 문자열 + 파일 확장자)

                File target = new File(uploadPath, saveName);//  파일 정보 저장
                file.transferTo(target);

                /* 파일 정보 저장 */
                AttachDto attach = new AttachDto();
                attach.setRefKey(refKey);
                attach.setDisplayName(file.getOriginalFilename());
                attach.setSaveName(saveName);
                attach.setSize((int) file.getSize());
                /* 파일 정보 추가 */
                attachList.add(attach);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return attachList;
    }
}
