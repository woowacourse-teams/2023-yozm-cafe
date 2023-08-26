import type { Meta, StoryObj } from '@storybook/react';
import { cafes } from '../data/mockData';
import CafeDetailBottomSheet from './CafeDetailBottomSheet';

type Story = StoryObj<typeof CafeDetailBottomSheet>;

const meta: Meta<typeof CafeDetailBottomSheet> = {
  title: 'CafeDetailBottomSheet',
  component: CafeDetailBottomSheet,
};

export default meta;

export const Default: Story = {
  args: {
    cafe: cafes[0],
  },
};
