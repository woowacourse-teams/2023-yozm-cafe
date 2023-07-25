import type { Meta, StoryObj } from '@storybook/react';
import Header from './Header';

type Story = StoryObj<typeof Header>;

const meta: Meta<typeof Header> = {
  title: 'Header',
  component: Header,
};

export default meta;

export const Default: Story = {
  args: {},
};
