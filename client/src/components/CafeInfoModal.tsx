import { useState } from 'react';
import { BiSolidInfoCircle } from 'react-icons/bi';
import { SlLocationPin } from 'react-icons/sl';
import { styled } from 'styled-components';

type CafeInfoModalProps = {
  title: string;
  address: string;
  content: string;
};

const CafeInfoModal = ({ title, address, content }: CafeInfoModalProps) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <Container>
      <Title>{title}</Title>
      <Address>
        <LocationPinIcon />
        {address}
      </Address>
      <Modal className={isOpen ? 'active' : ''} onClick={() => setIsOpen(!isOpen)}>
        {!isOpen && (
          <MoreInfoContainer>
            <MoreInfoContent>
              <SolidInfoCircleIcon />
              <Text>더보기</Text>
            </MoreInfoContent>
          </MoreInfoContainer>
        )}
        <Content>
          {title}
          {content}
        </Content>
      </Modal>
    </Container>
  );
};

export default CafeInfoModal;

const Container = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: column;

  height: 80px;
  max-height: 80px;
`;

const Text = styled.span`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.white};
`;

const MoreInfoContent = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const MoreInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  padding-right: ${({ theme }) => theme.space[5]};
`;

const SolidInfoCircleIcon = styled(BiSolidInfoCircle)`
  font-size: ${({ theme }) => theme.fontSize['4xl']};
  color: ${({ theme }) => theme.color.white};
`;

const LocationPinIcon = styled(SlLocationPin)`
  margin-right: ${({ theme }) => theme.space[1]};
  font-size: ${({ theme }) => theme.fontSize.sm};
`;

const Title = styled.h1`
  padding-bottom: ${({ theme }) => theme.space['1.5']};
  padding-left: ${({ theme }) => theme.space[3]};

  font-size: ${({ theme }) => theme.fontSize['3xl']};
  font-weight: bolder;
  color: ${({ theme }) => theme.color.white};
`;

const Address = styled.h2`
  padding-left: ${({ theme }) => theme.space[3]};
  font-size: ${({ theme }) => theme.fontSize.lg};
  color: ${({ theme }) => theme.color.white};
`;

const Content = styled.p`
  margin-top: ${({ theme }) => theme.space[5]};
  color: ${({ theme }) => theme.color.gray};
`;

const Modal = styled.main`
  position: absolute;
  width: 100%;

  &:not(.active) {
    cursor: pointer;
    height: 80px;
  }
  &.active {
    cursor: pointer;

    right: 0;
    bottom: 0;
    left: 0;

    height: 500px;

    opacity: 1;
    background: ${({ theme }) => theme.color.white};
    backdrop-filter: none;
    border-radius: 20px 20px 0 0;
  }

  & > ${Content} {
    opacity: 0;
  }

  &.active > ${Content} {
    opacity: 1;
  }
`;
