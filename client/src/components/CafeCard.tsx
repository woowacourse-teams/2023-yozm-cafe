import { useEffect, useRef } from 'react';
import { styled } from 'styled-components';
import useIntersection from '../hooks/useIntersection';
import { Cafe } from '../types';
import CafeActionBar from './CafeActionBar';
import CafeInfoModal from './CafeInfoModal';

type CardProps = {
  cafe: Cafe;
  onIntersect?: (intersection: IntersectionObserverEntry) => void;
};

const CafeCard = (props: CardProps) => {
  const { cafe, onIntersect } = props;

  const ref = useRef<HTMLImageElement>(null);
  const intersection = useIntersection(ref, { threshold: 0.7 });

  useEffect(() => {
    if (intersection) {
      onIntersect?.(intersection);
    }
  }, [intersection?.isIntersecting, onIntersect]);

  return (
    <Container>
      <Image ref={ref} src={cafe.images.urls[0]} />
      <AsidePosition>
        <Aside>
          <CafeInfoModal title={cafe.name} address={cafe.address} content={cafe.detail.description} />
          <CafeActionBar cafe={cafe} />
        </Aside>
      </AsidePosition>
    </Container>
  );
};

export default CafeCard;

const Container = styled.div`
  position: relative;
  overflow: hidden;
  height: 100%;
`;

const Image = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const AsidePosition = styled.div`
  position: absolute;
  bottom: 0;
  width: 100%;
`;

const Aside = styled.div`
  position: relative;

  display: flex;
  flex-direction: column-reverse;
  gap: 40px;

  margin: 20px;
`;
