import type { Meta, StoryObj } from '@storybook/react';
import LikeButton from './LikeButton';

type Story = StoryObj<typeof LikeButton>;

const meta: Meta<typeof LikeButton> = {
  title: 'LikeButton',
  component: LikeButton,
};

export default meta;

export const Default: Story = {
  args: { currentLikeCount: 1 },
};
