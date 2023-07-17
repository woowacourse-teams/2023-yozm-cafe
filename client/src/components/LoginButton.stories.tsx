import type { Meta, StoryObj } from '@storybook/react';
import LoginButton from './LoginButton';

type Story = StoryObj<typeof LoginButton>;

const meta: Meta<typeof LoginButton> = {
  title: 'LoginButton',
  component: LoginButton,
};

export default meta;

export const Default: Story = {
  args: {
    border: 'none',
    color: 'yellow',
  },
};
