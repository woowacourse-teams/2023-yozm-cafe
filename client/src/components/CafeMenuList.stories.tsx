import type { Meta, StoryObj } from '@storybook/react';
import { cafeMenus } from '../data/mockData';
import CafeMenuList from './CafeMenuList';

type Story = StoryObj<typeof CafeMenuList>;

const meta: Meta<typeof CafeMenuList> = {
  title: 'CafeMenuList',
  component: CafeMenuList,
};

export default meta;

export const Default: Story = {
  args: {
    menus: [...cafeMenus[0].menus, ...cafeMenus[0].menus, ...cafeMenus[0].menus],
  },
};
