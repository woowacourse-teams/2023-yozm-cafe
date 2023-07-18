import { styled } from 'styled-components';

type ProfileInfoProps = {
  userImage: string;
  userName: string;
};

const ProfileInfo = ({ userImage, userName }: ProfileInfoProps) => {
  return (
    <Container>
      <Image src={userImage} alt="프로필 이미지" />
      <Name>{userName}</Name>
    </Container>
  );
};

export default ProfileInfo;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Image = styled.img`
  width: 120px;
  height: 120px;
  border-radius: 50%;
`;

const Name = styled.span`
  margin-top: ${({ theme }) => theme.space['2.5']};
  font-size: ${({ theme }) => theme.fontSize.lg};
  font-weight: 600;
  color: ${({ theme }) => theme.color.text.primary};
`;
