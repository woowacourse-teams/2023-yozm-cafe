import ScrollSnapItem from '../ScrollSnapItem';

// 음수 mod 시 양수 값을 얻기 위한 함수
const mod = (n: number, m: number) => ((n % m) + m) % m;

type ScrollSnapCSSItemListProps<Item> = {
  scrollPosition: number;
  items: Item[];
  itemRenderer: (item: Item, index: number) => React.ReactNode;
  // focus된 아이템에서 일정 범위만큼 아이템을 렌더링
  renderDistance?: number;
};

/**
 * DOM scroll box에 의존적인 리스트
 *
 * CSS scroll-snap 사용 시 이 컴포넌트를 사용하여야 한다
 */
const ScrollSnapCSSItemList = <Item,>(props: ScrollSnapCSSItemListProps<Item>) => {
  const { scrollPosition, items, itemRenderer, renderDistance = 2 } = props;

  const focusedIndex = mod(Math.floor(scrollPosition), items.length);
  const visiblePosition = mod(scrollPosition, 1);

  return items.map((item, index) => {
    const offset = index - focusedIndex;

    return (
      <ScrollSnapItem key={index} offset={offset} position={visiblePosition}>
        {Math.abs(offset) <= renderDistance && itemRenderer(item, index)}
      </ScrollSnapItem>
    );
  });
};

export default ScrollSnapCSSItemList;
