import type { MouseEventHandler } from 'react';
import { useState } from 'react';
import { styled } from 'styled-components';
import Image from '../utils/Image';

type ImageModalProps = {
  imageUrls: string[];
  onClose: () => void;
};

const ImageModal = (props: ImageModalProps) => {
  const { imageUrls, onClose } = props;
  const [activeImage, setActiveImage] = useState(imageUrls[0]);

  const handleContainerClick: MouseEventHandler = (event) => {
    if (event.currentTarget !== event.target) return;

    onClose();
  };

  return (
    <Container>
      <ActiveImageContainer onClick={handleContainerClick}>
        <ActiveImage src={Image.getUrl({ size: 'original', filename: activeImage })} />
      </ActiveImageContainer>
      <ImageList>
        {imageUrls.map((imageUrl, index) => (
          <ImageListItem key={index}>
            <ImageListItemButton onClick={() => setActiveImage(imageUrl)}>
              <ImageListItemImage src={Image.getUrl({ size: 'original', filename: imageUrl })} />
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

const ActiveImageContainer = styled.div`
  touch-action: pan-x pan-y;

  display: flex;
  flex: 1;
  align-items: center;
  justify-content: center;
`;

const ActiveImage = styled.img`
  width: 100%;
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
  cursor: pointer;
  width: 100%;
  height: 100%;
`;

const ImageListItemImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;
