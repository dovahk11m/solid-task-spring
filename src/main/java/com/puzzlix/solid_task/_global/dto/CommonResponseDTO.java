package com.puzzlix.solid_task._global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommonResponseDTO<T> {

    //공통 DTO

    private boolean success;
    private T data;
    private String message;

//    private CommonResponseDTO(boolean success, T data, String message) {
//        this.success = success;
//        this.data = data;
//        this.message = message;
//    }
//    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    //정적 팩토리 메서드(
    //static은 객체 속성이 아니라 클래스에 포함된다.

    //성공응답
    public static <T> CommonResponseDTO<T> success(T data, String message) {
        return new CommonResponseDTO<>(true, data, message);
    }

    public static <T> CommonResponseDTO<T> success(T data) {
        return success(data, null);
    }

    //실패응답
    public static <T> CommonResponseDTO<T> error(String message) {
        return new CommonResponseDTO<>(false, null, message);
    }

    /*
    클라이언트 코드 (컨트롤러) 로 부터
    객체 생성과정을 완전히 분리하고 숨기는 것이 목표다.
    이 방식의 정신은 OCP 개방폐쇄 원칙과 닿아있다.

    new CommonResponseDTO(); X
    CommonResponseDTO.error("11") O
     */

}
