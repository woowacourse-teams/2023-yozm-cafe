import { BiSolidInfoCircle } from 'react-icons/bi';
import { SlLocationPin } from 'react-icons/sl';
import { styled } from 'styled-components';

type CafeSummaryProps = {
  title: string;
  address: string;
  onClick: React.MouseEventHandler<HTMLDivElement>;
};

const CafeSummary = (props: CafeSummaryProps) => {
  const { title, address, onClick } = props;

  return (
    <Container onClick={onClick} role="button" tabIndex={0}>
      <Summary>
        <Title>{title}</Title>
        <Address>
          <LocationPinIcon />
          {address}
        </Address>
      </Summary>
      <ButtonList>
        <Button>
          <SolidInfoCircleIcon />
          <ButtonText>더보기</ButtonText>
        </Button>
      </ButtonList>
    </Container>
  );
};

const Container = styled.div`
  cursor: pointer;

  position: absolute;
  bottom: 0;

  display: flex;

  width: 100%;
  margin-bottom: 38px;
  padding: ${({ theme }) => theme.space[3]};
`;

const Summary = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[3]};
`;

const Title = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['3xl']};
  font-weight: bolder;
`;

const Address = styled.h2`
  font-size: ${({ theme }) => theme.fontSize.lg};
`;

const LocationPinIcon = styled(SlLocationPin)`
  margin-right: ${({ theme }) => theme.space[1]};
  font-size: ${({ theme }) => theme.fontSize.sm};
`;

const ButtonList = styled.div`
  margin-left: auto;
  font-size: ${({ theme }) => theme.fontSize.sm};
`;

const ButtonText = styled.span``;

const Button = styled.button`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;

const SolidInfoCircleIcon = styled(BiSolidInfoCircle)`
  font-size: ${({ theme }) => theme.fontSize['4xl']};
`;

export default CafeSummary;
