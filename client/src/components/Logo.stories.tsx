import type { Meta, StoryObj } from '@storybook/react';
import Logo from './Logo';

type Story = StoryObj<typeof Logo>;

const meta: Meta<typeof Logo> = {
  title: 'Logo',
  component: Logo,
};

export default meta;

export const Default: Story = {
  args: {
    fontSize: '7xl',
  },
};
