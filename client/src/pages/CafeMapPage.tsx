import { Status, Wrapper } from '@googlemaps/react-wrapper';
import { type ReactElement } from 'react';
import styled from 'styled-components';
import CafeMap from '../components/CafeMap';
import LoadingBar from '../components/LoadingBar';
import AppError from '../errors/AppError';

const render = (status: Status): ReactElement => {
  if (status === Status.FAILURE) {
    throw new AppError('지도를 불러오는데 실패하였습니다. 잠시 후 다시 시도해주세요');
  }
  return (
    <Container>
      <LoadingBar />
    </Container>
  );
};

const CafeMapPage = () => {
  return (
    <Wrapper apiKey={`${process.env.GOOGLE_MAPS_API_KEY}`} render={render} libraries={['marker']}>
      <CafeMap />
    </Wrapper>
  );
};

export default CafeMapPage;

const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;
`;
