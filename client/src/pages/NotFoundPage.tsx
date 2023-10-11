import AppError from '../errors/AppError';

const NotFoundPage = () => {
  throw new AppError('경로를 찾을 수 없어요. 홈 화면으로 이동하거나 경로를 다시 한 번 확인해주시겠어요?');
};

export default NotFoundPage;
