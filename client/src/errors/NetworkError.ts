import AppError from './AppError';

class NetworkError extends AppError {
  constructor() {
    super('인터넷 연결에 문제가 생겼어요');
  }
}

export default NetworkError;
