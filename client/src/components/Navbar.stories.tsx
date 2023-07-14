import type { Meta, StoryObj } from '@storybook/react';
import Navbar from './Navbar';

type Story = StoryObj<typeof Navbar>;

const meta: Meta<typeof Navbar> = {
  title: 'Navbar',
  component: Navbar,
};

export default meta;

export const Default: Story = {
  args: {},
};
