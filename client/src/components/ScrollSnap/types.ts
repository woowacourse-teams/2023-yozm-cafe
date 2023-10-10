import type { ComponentPropsWithoutRef } from 'react';

export type TimingFn = (x: number) => number;

export type ScrollSnapProps<Item> = ComponentPropsWithoutRef<'div'> & {
  // position of currently active item. (equiv with index)
  // always 0..items.length (positive integer)
  activeIndex: number;
  onActiveIndexChange: (activeIndex: number) => void;
  // scroll position of container
  scrollPosition: number;
  onScrollPositionChange: (scrollPosition: number) => void;
  timingFn?: TimingFn;
  items: Item[];
  itemRenderer: (item: Item, index: number) => React.ReactNode;
  // enable continuity scrolling at the end of item
  enableRolling?: boolean;
};
