import { action } from '@storybook/addon-actions';
import type { Meta, StoryObj } from '@storybook/react';
import Modal from './Modal';

type Story = StoryObj<typeof Modal>;

const meta: Meta<typeof Modal> = {
  title: 'Modal',
  component: Modal,
};

export default meta;

export const Default: Story = {
  args: {
    onClose: action('close'),
  },
};
