import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import CafeCard from '../components/CafeCard';
import useCafe from '../hooks/useCafe';

const CafePage = () => {
  const { cafeId } = useParams();
  const { data: cafe } = useCafe(Number(cafeId));

  useEffect(() => {
    // 메타 태그 생성
    const metaImage = document.createElement('meta');
    metaImage.setAttribute('property', 'og:image');
    metaImage.setAttribute('content', cafe.images[0]);
    document.head.appendChild(metaImage);

    const metaDescription = document.createElement('meta');
    metaDescription.setAttribute('property', 'og:description');
    metaDescription.setAttribute('content', cafe.name);
    document.head.appendChild(metaDescription);

    return () => {
      // 컴포넌트가 언마운트될 때 메타 태그 제거
      document.head.removeChild(metaImage);
      document.head.removeChild(metaDescription);
    };
  }, [cafe, cafeId]);

  return (
    <CardList>
      <CafeCard cafe={cafe} />
    </CardList>
  );
};

export default CafePage;

const CardList = styled.ul`
  scroll-snap-type: y mandatory;
  overflow-y: scroll;
  height: 100%;

  & > * {
    scroll-snap-align: start;
    scroll-snap-stop: always;
  }
`;
