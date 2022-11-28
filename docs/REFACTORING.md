# 미션 - 숫자 야구

## 리펙토링 내용

- 마지막 미션의 추가 요구사항 도입
- `.gitignore` 내용 추가
- 패키지 구조 변경
- `FrontController` 패턴 삭제
- `DTO` 도입
- `GameController`가 외부에서 `View`를 주입받도록 변경
- `InputView, OutputView`에 싱글톤 패턴 적용
- 재할당되지 않는 메소드 매개변수 `final` 키워드 추가
- `NumberFormatException` 발생 시 이를 `IllegalArgumentException`으로 변경할 때 기존 예외를 저장하도록 변경
- 테스트 `Describe - Cotext - It` 패턴 적용
- 테스트에서 `private 필드`를 검증하던 내용 제거

## 학습 내용

- 싱글톤 패턴