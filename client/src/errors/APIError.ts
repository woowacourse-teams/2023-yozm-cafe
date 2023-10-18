import type { ErrorResponseBody } from '../types';
import AppError from './AppError';

/**
 * API 호출의 응답이 400, 500 등의 error response일 경우
 * 이를 wrapping하는 에러 클래스
 */
class APIError extends AppError {
  readonly response: Response;

  constructor(response: Response, body: unknown) {
    super(APIError.getErrorMessageFromBody(body));
    this.response = response;
  }

  /**
   * 서버 측에서 지정해 준 에러 코드 혹은 메세지가 포함되어 있는지 검사합니다
   */
  static isErrorResponseBody(body: unknown): body is ErrorResponseBody {
    return !!body && typeof body === 'object' && 'code' in body && 'message' in body;
  }

  /**
   * 응답 body에서 적절한 에러 메세지를 추출하여 반환합니다
   *
   * @param body 에러가 포함될 수 있는 모든 객체
   * @returns 에러 메세지
   */
  static getErrorMessageFromBody(body: unknown): string {
    if (APIError.isErrorResponseBody(body)) {
      return body.message;
    }
    if (!!body && typeof body === 'object') {
      if ('message' in body) {
        return String(body.message);
      }

      return JSON.stringify(body);
    }
    return String(body);
  }
}

export default APIError;
