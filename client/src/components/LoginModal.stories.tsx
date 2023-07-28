import { action } from '@storybook/addon-actions';
import type { Meta, StoryObj } from '@storybook/react';
import LoginModal from './LoginModal';

type Story = StoryObj<typeof LoginModal>;

const meta: Meta<typeof LoginModal> = {
  title: 'LoginModal',
  component: LoginModal,
};

export default meta;

export const Default: Story = {
  args: {
    onClose: action('close'),
  },
};
