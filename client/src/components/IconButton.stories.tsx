import type { Meta, StoryObj } from '@storybook/react';
import { FaShare } from 'react-icons/fa';
import IconButton from './IconButton';

type Story = StoryObj<typeof IconButton>;

const meta: Meta<typeof IconButton> = {
  title: 'IconButton',
  component: IconButton,
};

export default meta;

export const Default: Story = {
  args: {
    children: <FaShare />,
  },
};

export const WithLabel: Story = {
  args: {
    children: <FaShare />,
    label: '공유',
  },
};
