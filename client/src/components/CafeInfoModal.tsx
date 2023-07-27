import { useState } from 'react';
import { RiArrowUpDoubleLine } from 'react-icons/ri';
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
        <StyledLocation />
        {address}
      </Address>
      <Modal className={isOpen ? 'active' : ''} onClick={() => setIsOpen(!isOpen)}>
        {!isOpen && (
          <ArrowDownContainer>
            <StyledArrowDown />
          </ArrowDownContainer>
        )}
        <Content>{content}</Content>
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
  margin-left: 8px;
  padding: 0 8px;
`;

const StyledArrowDown = styled(RiArrowUpDoubleLine)`
  font-size: ${({ theme }) => theme.fontSize['3xl']};
  color: ${({ theme }) => theme.color.white};
`;

const StyledLocation = styled(SlLocationPin)`
  margin: 1px;
  font-size: ${({ theme }) => theme.fontSize.sm};
`;

const ArrowDownContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const Title = styled.h1`
  padding-bottom: 5px;
  font-size: ${({ theme }) => theme.fontSize['3xl']};
  font-weight: bolder;
  color: ${({ theme }) => theme.color.white};
`;

const Address = styled.h2`
  font-size: ${({ theme }) => theme.fontSize.lg};
  color: ${({ theme }) => theme.color.white};
`;

const Content = styled.p`
  margin-top: 20px;
  color: ${({ theme }) => theme.color.gray};
`;

const Modal = styled.main`
  will-change: transform;

  position: absolute;
  bottom: 0;

  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 8px;

  width: 100%;
  height: 90px;
  padding: 16px;

  transition: all 0.3s;

  &:not(.active):hover {
    cursor: pointer;
    height: 100px;
  }

  &.active {
    right: 0;
    bottom: 0;
    left: 0;

    height: 500px;

    opacity: 1;
    background: ${({ theme }) => theme.color.white};
    backdrop-filter: none;
  }

  & > ${Content} {
    opacity: 0;
    transition: opacity 0.2s;
  }

  &.active > ${Content} {
    opacity: 1;
  }
`;
