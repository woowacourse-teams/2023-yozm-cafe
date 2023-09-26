import type { FormEventHandler } from 'react';
import { useDeferredValue, useEffect, useState } from 'react';
import { BiCoffeeTogo, BiHome, BiSearch } from 'react-icons/bi';
import { PiHeartFill } from 'react-icons/pi';
import { Link, useSearchParams } from 'react-router-dom';
import styled from 'styled-components';
import SearchInput from '../components/SearchInput';
import useSearchCafes from '../hooks/useSearchCafes';
import type { Theme } from '../styles/theme';

const SearchPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();

  const [searchName, setSearchName] = useState(searchParams.get('cafeName') || '');
  const [searchAddress, setSearchAddress] = useState(searchParams.get('address') || '');
  const [searchMenu, setSearchMenu] = useState(searchParams.get('menu') || '');

  const [isDetailEnabled, setIsDetailEnabled] = useState(false);

  const query = useDeferredValue(isDetailEnabled ? { searchName, searchAddress, searchMenu } : { searchName });
  const isQueryFilled = searchName || searchAddress || searchMenu;
  const { data: searchedCafes } = useSearchCafes(query);

  const handleSearch: FormEventHandler = (event) => {
    event.preventDefault();
  };

  useEffect(() => {
    const cleanedSearchParams = Object.fromEntries(
      Object.entries({ cafeName: searchName, address: searchAddress, menu: searchMenu }).filter(([, value]) => !!value),
    );
    setSearchParams(new URLSearchParams(cleanedSearchParams));
  }, [searchName, searchAddress, searchMenu]);

  return (
    <Container>
      <Spacer $size="6" />
      <Title>☕ 카페를 검색해보세요!</Title>
      <Spacer $size="4" />

      <Form onSubmit={handleSearch}>
        <FormGroup>
          <SearchInput variant="large" value={searchName} onChange={(event) => setSearchName(event.target.value)} />
          <SearchButton>
            <BiSearch />
          </SearchButton>
        </FormGroup>

        <Spacer $size="2" />

        <SearchDetailsButton onClick={() => setIsDetailEnabled(!isDetailEnabled)}>
          주소나 메뉴 이름으로 검색하기
        </SearchDetailsButton>
        <Spacer $size="4" />
        <SearchDetails $show={isDetailEnabled}>
          <FormGroup>
            <StartIcon>
              <BiHome />
            </StartIcon>
            <SearchInput
              placeholder="찾고 싶은 카페의 주소"
              value={searchAddress}
              onChange={(event) => setSearchAddress(event.target.value)}
            />
          </FormGroup>
          <FormGroup>
            <StartIcon>
              <BiCoffeeTogo />
            </StartIcon>
            <SearchInput
              placeholder="찾고 싶은 메뉴의 이름"
              value={searchMenu}
              onChange={(event) => setSearchMenu(event.target.value)}
            />
          </FormGroup>
        </SearchDetails>
        <Spacer $size="8" />
      </Form>

      {searchedCafes.length > 0 ? (
        <CafeList>
          {searchedCafes.map((cafe) => (
            <Link key={cafe.id} to={`/cafes/${cafe.id}`}>
              <CafeListItem>
                <CafeImage src={cafe.image} />
                <CafeInfo>
                  <CafeName>{cafe.name}</CafeName>
                  <CafeAddress>{cafe.address}</CafeAddress>
                </CafeInfo>
                <CafeLikes>
                  <PiHeartFill /> {cafe.likeCount}
                </CafeLikes>
              </CafeListItem>
            </Link>
          ))}
        </CafeList>
      ) : (
        isQueryFilled && <CafeListEmpty>일치하는 검색 결과가 없습니다!</CafeListEmpty>
      )}
    </Container>
  );
};

export default SearchPage;

const Container = styled.main`
  padding: ${({ theme }) => theme.space[8]};
`;

const Form = styled.form``;

const Spacer = styled.div<{ $size: keyof Theme['space'] }>`
  min-height: ${({ theme, $size }) => theme.space[$size]};
`;

const Title = styled.h1`
  margin-bottom: ${({ theme }) => theme.space[4]};
  font-size: ${({ theme }) => theme.fontSize['3xl']};
  font-weight: 100;
  text-align: center;
`;

const SearchButton = styled.button`
  padding: ${({ theme }) => theme.space[2]};
  font-size: ${({ theme }) => theme.fontSize['2xl']};
`;

const SearchDetailsButton = styled.button.attrs({ type: 'button' })`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.gray};
  background: none;
`;

const SearchDetails = styled.div<{ $show: boolean }>`
  display: ${({ $show }) => ($show ? 'flex' : 'none')};
  flex-direction: column;
  gap: ${({ theme }) => theme.space[4]};
`;

const StartIcon = styled.div`
  padding: ${({ theme }) => theme.space[0.5]};
  font-size: ${({ theme }) => theme.fontSize['2xl']};
`;

const FormGroup = styled.fieldset`
  display: flex;
  gap: ${({ theme }) => theme.space[4]};
  align-items: center;
`;

const CafeList = styled.ul``;

const CafeListItem = styled.li`
  display: flex;
  gap: ${({ theme }) => theme.space[4]};
  align-items: center;
  padding: ${({ theme }) => theme.space[2]};

  &:hover {
    cursor: pointer;
    background: #eeeeee;
  }
`;

const CafeImage = styled.img`
  aspect-ratio: 1 / 1;
  height: 48px;
  object-fit: cover;
  border-radius: 50%;
`;

const CafeInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[1]};
`;

const CafeName = styled.h3``;

const CafeAddress = styled.div`
  font-size: ${({ theme }) => theme.fontSize.xs};
  color: ${({ theme }) => theme.color.gray};
`;

const CafeListEmpty = styled.div`
  padding: ${({ theme }) => theme.space[10]} 0;
  color: ${({ theme }) => theme.color.gray};
  text-align: center;
`;

const CafeLikes = styled.div`
  margin-left: auto;
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.secondary};
`;
