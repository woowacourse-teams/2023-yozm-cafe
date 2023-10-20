import type { MouseEventHandler } from 'react';
import { useState } from 'react';
import { CgClose } from 'react-icons/cg';
import { styled } from 'styled-components';
import { useScrollSnapGuard } from 'yozm-cafe-react-scroll-snap';
import Resource from '../utils/Resource';

type ImageModalProps = {
  imageUrls: string[];
  onClose: () => void;
};

const ImageModal = (props: ImageModalProps) => {
  const { imageUrls, onClose } = props;
  const [activeImage, setActiveImage] = useState(imageUrls[0]);
  const scrollSnapGuardHandlers = useScrollSnapGuard();

  const handleContainerClick: MouseEventHandler = (event) => {
    if (event.currentTarget !== event.target) return;

    onClose();
  };

  return (
    <Container {...scrollSnapGuardHandlers}>
      <Actions>
        <button onClick={onClose}>
          <CloseIcon />
        </button>
      </Actions>
      <ActiveImageContainer onClick={handleContainerClick}>
        <ActiveImage src={Resource.getImageUrl({ size: 'original', filename: activeImage })} />
      </ActiveImageContainer>
      <ImageList>
        {imageUrls.map((imageUrl, index) => (
          <ImageListItem key={index}>
            <ImageListItemButton onClick={() => setActiveImage(imageUrl)}>
              <ImageListItemImage src={Resource.getImageUrl({ size: 'original', filename: imageUrl })} />
            </ImageListItemButton>
          </ImageListItem>
        ))}
      </ImageList>
    </Container>
  );
};

export default ImageModal;

const Container = styled.div`
  position: absolute;
  z-index: 1000;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;

  display: flex;
  flex-direction: column;

  background: rgba(0, 0, 0, 0.5);
`;

const Actions = styled.div`
  display: flex;
  justify-content: flex-end;
  padding: ${({ theme }) => theme.space[4]};
`;

const CloseIcon = styled(CgClose)`
  font-size: ${({ theme }) => theme.fontSize['4xl']};
  color: white;
`;

const ActiveImageContainer = styled.div`
  touch-action: pan-x pan-y;

  position: relative;

  display: flex;
  flex: 1;
  align-items: center;
  justify-content: center;

  margin-top: ${({ theme }) => theme.space[4]};
  margin-bottom: ${({ theme }) => theme.space[4]};
`;

const ActiveImage = styled.img`
  position: absolute;
  max-height: 100%;
`;

const ImageList = styled.ul`
  overflow-x: auto;
  display: flex;
  height: 80px;
  margin-top: auto;
`;

const ImageListItem = styled.li`
  flex-shrink: 0;
  width: 80px;
`;

const ImageListItemButton = styled.button`
  width: 100%;
  height: 100%;
`;

const ImageListItemImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;
