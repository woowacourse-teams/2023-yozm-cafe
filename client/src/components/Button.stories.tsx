import type { Meta, StoryObj } from '@storybook/react';
import Button from './Button';

type Story = StoryObj<typeof Button>;

const meta: Meta<typeof Button> = {
  title: 'Button',
  component: Button,
};

export default meta;

export const Default: Story = {
  args: {
    children: '버튼',
    fullWidth: true,
  },
};
