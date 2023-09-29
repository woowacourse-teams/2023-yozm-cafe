import type { MouseEventHandler } from 'react';
import { useCallback, useEffect, useSyncExternalStore } from 'react';
import { createRoot } from 'react-dom/client';
import { TfiClose } from 'react-icons/tfi';
import { styled } from 'styled-components';
import type { CafeMapMarker } from '../types';
import Observer from '../utils/Observer';

const openedCafeIdObserver = new Observer<number | null>(null);

type CafeMarkerProps = {
  cafe: CafeMapMarker;
  onOpenModal: () => void;
  onCloseModal: () => void;
};

const useObserver = <T,>(observer: Observer<T>): [T, (state: T) => void] => {
  const state = useSyncExternalStore(
    (callback) => {
      observer.subscribe(callback);

      return () => observer.unsubscribe(callback);
    },
    () => observer.getState(),
  );
  const dispatch = (state: T) => observer.setState(state);
  return [state, dispatch];
};

const CafeMarker = (props: CafeMarkerProps) => {
  const { cafe, onOpenModal, onCloseModal } = props;

  const [openedCafeId, setOpenedCafeId] = useObserver(openedCafeIdObserver);

  const handleCloseModal = useCallback(() => {
    setOpenedCafeId(null);
    onCloseModal();
  }, []);

  const handleOpenModal: MouseEventHandler = useCallback(() => {
    setOpenedCafeId(cafe.id);
    onOpenModal();
  }, []);

  useEffect(() => {
    return () => window.removeEventListener('click', handleCloseModal);
  }, []);

  return (
    <div onClick={(event) => event.stopPropagation()}>
      {openedCafeId === cafe.id && (
        <ModalContainer>
          <ModalCloseButtonContainer>
            <ModalCloseButton onClick={handleCloseModal}>
              <TfiClose />
            </ModalCloseButton>
          </ModalCloseButtonContainer>
          <ModalTitle>{cafe.name}</ModalTitle>
          <ModalSubtitle>{cafe.address}</ModalSubtitle>
          <ModalContent></ModalContent>
          <ModalButtonContainer>
            <StyledLink href={`https://yozm.cafe/cafes/${cafe.id}`} target="_blank" rel="noreferrer">
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
  cafe: CafeMapMarker;
};

const CafeMarkers = (props: CafeMarkersProps) => {
  const { map, cafe } = props;
  const { latitude, longitude, name } = cafe;

  useEffect(() => {
    const container = document.createElement('div');
    const markerRoot = createRoot(container);

    const newMarker = new google.maps.marker.AdvancedMarkerElement({
      position: { lat: latitude, lng: longitude },
      map,
      title: name,
      content: container,
    });

    markerRoot.render(
      <CafeMarker
        cafe={cafe}
        onOpenModal={() => {
          newMarker.zIndex = 1;
        }}
        onCloseModal={() => {
          newMarker.zIndex = 0;
        }}
      />,
    );

    // eslint-disable-next-line @typescript-eslint/no-empty-function
    const noop = () => {};

    newMarker.addListener('click', noop);

    return () => newMarker.removeEventListener('click', noop);
  }, []);

  return <></>;
};

export default CafeMarkers;

const Marker = styled.button`
  position: absolute;
  transform: translate(-50%, -50%);
`;

const MarkerIcon = styled.img.attrs({ src: '/images/coffee-icon.png', alt: '카페마커' })``;

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
  cursor: pointer;

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
