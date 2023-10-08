/**
 * 앱에서 의도적으로 발생시킨 에러이며, 복구 가능한 에러이다
 *
 * 사용자가 조치할 수 있는 수준의 에러인 경우 `AppError` 혹은 그 자식 클래스여야 한다
 *
 * @example
 * if (!user.isAdmin) {
 *   throw new AppError('이 자원에 접근할 수 없습니다. 권한이 부족합니다.');
 * }
 */
class AppError extends Error {}

export default AppError;
