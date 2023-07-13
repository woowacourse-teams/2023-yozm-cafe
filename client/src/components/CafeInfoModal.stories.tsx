import CafeInfoModal from './CafeInfoModal';
import type { Meta, StoryObj } from '@storybook/react';

type Story = StoryObj<typeof CafeInfoModal>;
const meta: Meta<typeof CafeInfoModal> = {
  title: 'CafeInfoModal',
  component: CafeInfoModal,
};
export default meta;

export const Default: Story = {
  args: {
    title: '성수동 카페',
    address: '서울 성동구 연무장3길 6',
    content: '안녕하세요~!!!!!',
  },
};
