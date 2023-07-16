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
    width: '100px',
    height: '50px',
    border: 'none',
    fontWeight: '600',
    color: 'primary',
  },
};
