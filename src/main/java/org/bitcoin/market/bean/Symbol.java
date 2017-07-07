package org.bitcoin.market.bean;

/**
 * Created by lichang on 14-2-26.
 */

public enum Symbol {
    eos {
        @Override
        public boolean isEos() {
            return true;
        }
        @Override
        public boolean isBtc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    }, btc {
        @Override
        public boolean isEos() {
            return false;
        }
        @Override
        public boolean isBtc() {
            return true;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    }, ltc {
        @Override
        public boolean isEos() {
            return false;
        }
        @Override
        public boolean isBtc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return true;
        }

        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    }, usd {
        @Override
        public boolean isEos() {
            return false;
        }
        @Override
        public boolean isBtc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return true;
        }

        @Override
        public boolean isCny() {
            return false;
        }
    }, cny {
        @Override
        public boolean isEos() {
            return false;
        }
        @Override
        public boolean isBtc() {
            return false;
        }

        @Override
        public boolean isLtc() {
            return false;
        }

        @Override
        public boolean isUsd() {
            return false;
        }

        @Override
        public boolean isCny() {
            return true;
        }
    };

    public abstract boolean isBtc();

    public abstract boolean isEos();

    public abstract boolean isLtc();

    public abstract boolean isUsd();

    public abstract boolean isCny();
}

