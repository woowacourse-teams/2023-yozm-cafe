import type { Meta, StoryObj } from '@storybook/react';
import LikedCafeList from './LikedCafeList';

type Story = StoryObj<typeof LikedCafeList>;

const meta: Meta<typeof LikedCafeList> = {
  title: 'LikedCafeList',
  component: LikedCafeList,
};

export default meta;

export const Default: Story = {
  args: {},
};
