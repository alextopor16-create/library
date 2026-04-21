public class LibExt extends RuntimeException {
        private final ErrMsg errMsg;

        public LibExt(ErrMsg errMsg) {
            super(errMsg.getMsg());
            this.errMsg = errMsg;
        }

        public ErrMsg getErrMsg() {
            return errMsg;
        }

}
