import { Status, Wrapper } from '@googlemaps/react-wrapper';
import { type ReactElement } from 'react';
import CafeMap from '../components/CafeMap';
import AppError from '../errors/AppError';

const render = (status: Status): ReactElement => {
  if (status === Status.FAILURE) {
    throw new AppError('지도를 불러오는데 실패하였습니다. 잠시 후 다시 시도해주세요');
  }
  return <div>로딩중...</div>;
};

const CafeMapPage = () => {
  return (
    <Wrapper apiKey={`${process.env.GOOGLE_MAPS_API_KEY}`} render={render} libraries={['marker']}>
      <CafeMap />
    </Wrapper>
  );
};

export default CafeMapPage;
