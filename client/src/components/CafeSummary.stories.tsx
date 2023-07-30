import type { Meta, StoryObj } from '@storybook/react';
import CafeSummary from './CafeSummary';

type Story = StoryObj<typeof CafeSummary>;

const meta: Meta<typeof CafeSummary> = {
  title: 'CafeSummary',
  component: CafeSummary,
};

export default meta;

export const Default: Story = {
  args: {
    title: '성수동 카페',
    address: '서울 성동구 연무장3길 6',
  },
};
