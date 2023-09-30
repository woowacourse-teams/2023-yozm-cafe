import type { MouseEventHandler } from 'react';
import { useCallback, useEffect } from 'react';
import { createRoot } from 'react-dom/client';
import { TfiClose } from 'react-icons/tfi';
import { styled } from 'styled-components';
import useObservable from '../hooks/useObservable';
import type { CafeMapLocation } from '../types';
import Observable from '../utils/Observable';

const openedCafeIdObserverable = new Observable<number | null>(null);

type CafeMarkerProps = {
  cafeLocation: CafeMapLocation;
};

const CafeMarker = (props: CafeMarkerProps) => {
  const { cafeLocation } = props;
  const [openedCafeId, setOpenedCafeId] = useObservable(openedCafeIdObserverable);

  const handleCloseModal = useCallback(() => {
    setOpenedCafeId(null);
  }, []);

  const handleOpenModal: MouseEventHandler = useCallback(() => {
    setOpenedCafeId(cafeLocation.id);
  }, []);

  return (
    <div onClick={(event) => event.stopPropagation()}>
      {openedCafeId === cafeLocation.id && (
        <ModalContainer>
          <ModalCloseButtonContainer>
            <ModalCloseButton onClick={handleCloseModal}>
              <TfiClose />
            </ModalCloseButton>
          </ModalCloseButtonContainer>
          <ModalTitle>{cafeLocation.name}</ModalTitle>
          <ModalSubtitle>{cafeLocation.address}</ModalSubtitle>
          <ModalContent></ModalContent>
          <ModalButtonContainer>
            <StyledLink href={`https://yozm.cafe/cafes/${cafeLocation.id}`} target="_blank" rel="noreferrer">
              <ModalDetailButton onClick={handleCloseModal}>상세보기</ModalDetailButton>
            </StyledLink>
          </ModalButtonContainer>
        </ModalContainer>
      )}
      <Marker onClick={handleOpenModal}>
        <MarkerIcon />
      </Marker>
    </div>
  );
};

type CafeMarkersProps = {
  map: google.maps.Map;
  cafeLocation: CafeMapLocation;
};

const CafeMarkers = (props: CafeMarkersProps) => {
  const { map, cafeLocation } = props;
  const { latitude, longitude, name } = cafeLocation;

  useEffect(() => {
    const container = document.createElement('div');
    const markerRoot = createRoot(container);

    const newMarker = new google.maps.marker.AdvancedMarkerElement({
      position: { lat: latitude, lng: longitude },
      map,
      title: name,
      content: container,
    });

    const handleOpenedCafeIdChange = () => {
      const isOpened = openedCafeIdObserverable.getState() === cafeLocation.id;
      newMarker.zIndex = isOpened ? 1 : 0;
    };

    openedCafeIdObserverable.subscribe(handleOpenedCafeIdChange);

    markerRoot.render(<CafeMarker cafeLocation={cafeLocation} />);

    // eslint-disable-next-line @typescript-eslint/no-empty-function
    const noop = () => {};

    newMarker.addListener('click', noop);

    return () => {
      openedCafeIdObserverable.unsubscribe(handleOpenedCafeIdChange);
    };
  }, []);

  return <></>;
};

export default CafeMarkers;

const Marker = styled.button`
  position: absolute;
  transform: translate(-50%, -50%);
`;

const MarkerIcon = styled.img.attrs({ src: '/assets/coffee-icon.png', alt: '카페마커' })``;

const ModalContainer = styled.div`
  position: absolute;
  transform: translate(-50%, -100%) translateY(-30px) translateZ(100px);

  width: max-content;
  min-width: 160px;
  padding: 16px;

  background: white;
  border: 1px solid rgba(0, 0, 0, 0.05);
  border-radius: 10px;
  box-shadow: rgba(0, 0, 0, 0.12) 0px 2px 4px 0px;

  &::before {
    content: '';

    position: absolute;
    top: 100%;
    left: 50%;
    transform: translateX(-50%);

    border-color: white transparent transparent transparent;
    border-style: solid;
    border-width: 10px;
  }
`;

const ModalTitle = styled.h2`
  margin-bottom: 8px;
  font-size: 20px;
`;

const ModalSubtitle = styled.h3`
  font-size: 12px;
`;

const ModalContent = styled.div`
  margin-bottom: 16px;
`;

const ModalButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

const ModalCloseButtonContainer = styled.div`
  display: flex;
  flex-direction: row-reverse;
`;

const StyledLink = styled.a`
  width: 100%;
`;

const ModalCloseButton = styled.button``;

const ModalDetailButton = styled.button`
  width: 100%;
  padding: 8px 16px;

  color: white;

  background-color: #f08080;
  border: none;
  border-radius: 10px;
  &:hover {
    background-color: #f4a9a8;
  }
`;
