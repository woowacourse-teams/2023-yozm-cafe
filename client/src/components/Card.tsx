import { styled } from 'styled-components';

type CardProps = {
  imageUrl: string;
};

const Card = (props: CardProps) => {
  const { imageUrl } = props;

  return (
    <Container>
      <Padding />
      <Image src={imageUrl} />
    </Container>
  );
};

export default Card;

const Container = styled.div`
  position: relative;
  overflow: hidden;
  height: 100%;
`;

const Padding = styled.div`
  height: 60px;
`;

const Image = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 20px;
`;
