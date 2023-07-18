import type { Meta, StoryObj } from '@storybook/react';
import ProfileInfo from './ProfileInfo';

type Story = StoryObj<typeof ProfileInfo>;

const meta: Meta<typeof ProfileInfo> = {
  title: 'ProfileInfo',
  component: ProfileInfo,
};

export default meta;

export const Default: Story = {
  args: {
    userImage: '/images/profile-example.png',
    userName: '김고니',
  },
};
