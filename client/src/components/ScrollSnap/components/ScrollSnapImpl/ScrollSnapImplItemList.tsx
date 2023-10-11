// 음수 mod 시 양수 값을 얻기 위한 함수
const mod = (n: number, m: number) => ((n % m) + m) % m;

type ScrollSnapImplItemListProps<Item> = {
  scrollPosition: number;
  items: Item[];
  itemRenderer: (item: Item, index: number) => React.ReactNode;
  enableRolling?: boolean;
};

const ScrollSnapImplItemList = <Item,>(props: ScrollSnapImplItemListProps<Item>) => {
  const { scrollPosition, items, itemRenderer, enableRolling } = props;

  // position of item, which user sees
  // always positive integer
  // 현재 화면에 표시되고 있는 아이템의 index
  const focusedIndex = mod(Math.floor(scrollPosition), items.length);

  const indexedItems = items.map((item, index) => ({ index, item }));

  // 현재 화면의 아이템 및 위, 아래의 아이템을 표시한다
  const visibleItems = enableRolling
    ? [
        indexedItems.at(focusedIndex - 1), // 현재에서 상단 (혹은 끝) 아이템
        indexedItems.at(focusedIndex), // 현재 아이템
        indexedItems.at((focusedIndex + 1) % indexedItems.length), // 현재에서 하단 (혹은 첫) 아이템
      ]
    : [
        indexedItems[focusedIndex - 1], // 현재에서 상단 아이템
        indexedItems[focusedIndex], // 현재 아이템
        indexedItems[focusedIndex + 1], // 현재에서 하단 아이템
      ];
  const visiblePosition = mod(scrollPosition, 1);

  return (
    <div
      style={{
        width: '100%',
        height: '100%',
        display: 'grid',
      }}
    >
      {([0, 1, -1] as const)
        .map((visibleIndex) => ({ ...visibleItems[1 + visibleIndex], visibleIndex }))
        .map(({ item, index, visibleIndex }) => (
          <div
            key={index}
            style={{
              height: '100%',
              gridArea: '1 / 1 / 1 / 1',
              transform: `translateY(${(visibleIndex + -visiblePosition) * 100}%)`,
            }}
          >
            {item && typeof index === 'number' && itemRenderer(item, index)}
          </div>
        ))}
    </div>
  );
};

export default ScrollSnapImplItemList;
