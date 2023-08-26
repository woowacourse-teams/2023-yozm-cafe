import type { Meta, StoryObj } from '@storybook/react';
import { cafeMenus } from '../data/mockData';
import CafeMenuMiniList from './CafeMenuMiniList';

type Story = StoryObj<typeof CafeMenuMiniList>;

const meta: Meta<typeof CafeMenuMiniList> = {
  title: 'CafeMenuMiniList',
  component: CafeMenuMiniList,
};

export default meta;

export const Default: Story = {
  args: {
    menus: [...cafeMenus[0].menus, ...cafeMenus[0].menus, ...cafeMenus[0].menus],
  },
};
