import AppError from './AppError';

/**
 * 네트워크 오류에 해당되는 에러 클래스이다
 */
class NetworkError extends AppError {
  constructor() {
    super('인터넷 연결에 문제가 생겼어요');
  }
}

export default NetworkError;
