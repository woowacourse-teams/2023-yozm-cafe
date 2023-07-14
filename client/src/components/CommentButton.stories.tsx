import type { Meta, StoryObj } from '@storybook/react';
import CommentButton from './CommentButton';

type Story = StoryObj<typeof CommentButton>;

const meta: Meta<typeof CommentButton> = {
  title: 'CommentButton',
  component: CommentButton,
};
export default meta;

export const Default: Story = {
  args: {},
};
