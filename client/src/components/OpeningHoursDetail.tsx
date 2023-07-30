import { styled } from 'styled-components';
import { OpeningHour, OpeningHourDay } from '../types';

const DAY_MAPPER: Record<OpeningHourDay, string> = {
  MONDAY: '월',
  TUESDAY: '화',
  WEDNESDAY: '수',
  THURSDAY: '목',
  FRIDAY: '금',
  SATURDAY: '토',
  SUNDAY: '일',
};

type OpeningHoursDetailProps = {
  openingHours: OpeningHour[];
};

const OpeningHoursDetail = ({ openingHours }: OpeningHoursDetailProps) => {
  const getCurrentTime = (): string => {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');

    return `${hours}:${minutes}`;
  };

  const today = new Date().toLocaleDateString('en-US', { weekday: 'long' }).toUpperCase();
  const todayOpeningHour = openingHours.find((openingHour) => openingHour.day === today) ?? null;

  const isOpenedToday = (): boolean => {
    const currentTime = getCurrentTime();

    if (!todayOpeningHour || !todayOpeningHour.opened) return false;

    return (
      today === todayOpeningHour.day && todayOpeningHour.open <= currentTime && currentTime <= todayOpeningHour.close
    );
  };

  return (
    <Container>
      <h3>{isOpenedToday() ? '영업중' : '영업 종료'}</h3>
      <OpeningHourList>
        <OpeningHourListSummary>
          {todayOpeningHour
            ? `${DAY_MAPPER[todayOpeningHour.day]} ${todayOpeningHour.open} - ${todayOpeningHour.close}`
            : '휴무'}
        </OpeningHourListSummary>

        <ul>
          {openingHours.map((openingHour) => (
            <li key={openingHour.day}>
              {DAY_MAPPER[openingHour.day]} {openingHour.open} - {openingHour.close}
            </li>
          ))}
        </ul>
      </OpeningHourList>
    </Container>
  );
};

export default OpeningHoursDetail;

const Container = styled.div`
  display: flex;
  gap: ${({ theme }) => theme.space[2]};
`;

const OpeningHourListSummary = styled.summary``;

const OpeningHourList = styled.details`
  & ul {
    display: flex;
    flex-direction: column;
    gap: ${({ theme }) => theme.space[1]};
  }

  &[open] summary {
    margin-bottom: ${({ theme }) => theme.space[3]};
  }

  & summary::marker {
    content: '';
  }

  & summary::after {
    content: '▼';
    margin-left: ${({ theme }) => theme.space[1]};
    font-size: ${({ theme }) => theme.fontSize.sm};
  }

  &[open] summary::after {
    content: '▲';
  }
`;
