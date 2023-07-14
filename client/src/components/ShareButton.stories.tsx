import type { Meta, StoryObj } from '@storybook/react';
import ShareButton from './ShareButton';

type Story = StoryObj<typeof ShareButton>;

const meta: Meta<typeof ShareButton> = {
  title: 'ShareButton',
  component: ShareButton,
};
export default meta;

export const Default: Story = {
  args: {},
};
