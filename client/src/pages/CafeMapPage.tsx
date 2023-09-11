import { Status, Wrapper } from '@googlemaps/react-wrapper';
import { type ReactElement } from 'react';
import CafeMap from '../components/CafeMap';

const render = (status: Status): ReactElement => {
  if (status === Status.FAILURE) return <div>에러입니다.</div>;
  return <div>로딩중...</div>;
};

const CafeMapPage = () => {
  return (
    <Wrapper apiKey={`${process.env.GOOGLE_MAPS_API_KEY}`} render={render}>
      <CafeMap />
    </Wrapper>
  );
};

export default CafeMapPage;
